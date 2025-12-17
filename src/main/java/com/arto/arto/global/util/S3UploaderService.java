package com.arto.arto.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import org.springframework.beans.factory.annotation.Value; // @Value 사용을 위해 추가

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploaderService {

    private final S3Client s3Client; // Spring Cloud AWS가 자동 생성하여 주입

    // application.yaml에서 설정한 버킷 이름을 주입받음
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3에 파일을 업로드하고, 업로드된 파일의 URL을 반환합니다.
     * @param multipartFile 업로드할 파일
     * @param dirName S3 버킷 내의 폴더 경로 (예: "artwork-images")
     * @return S3에 저장된 파일의 공개 URL
     */
    public String uploadFile(MultipartFile multipartFile, String dirName) {
        if (multipartFile.isEmpty()) {
            // TODO: CustomException 처리 로직 추가
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        // 1. 파일 이름 생성 및 경로 설정
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 파일명이 중복되지 않도록 UUID와 폴더 경로를 사용
        String fileName = dirName + "/" + UUID.randomUUID() + extension;

        try {
            // 2. S3 요청 객체 생성 (PUT 요청)
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName) // S3에 저장될 파일 경로 및 이름
                    .contentType(multipartFile.getContentType()) // 파일 타입 지정
                    .contentLength(multipartFile.getSize()) // 파일 크기 지정
                    .build();

            // 3. S3에 파일 업로드 실행
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(multipartFile.getBytes()));

            // 4. 업로드된 파일의 URL 반환
            // AWS S3 객체 URL 형식: https://[버킷이름].s3.[리전].amazonaws.com/[파일명]
            return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(fileName)).toString();

        } catch (IOException e) {
            // 파일 처리 중 발생한 IO 예외
            // TODO: CustomException 처리 로직 추가
            throw new RuntimeException("파일 업로드 중 IO 오류가 발생했습니다.", e);
        } catch (S3Exception e) {
            // S3 통신 중 발생한 AWS 예외
            // TODO: CustomException 처리 로직 추가
            throw new RuntimeException("S3 업로드 중 AWS 오류가 발생했습니다.", e);
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            // fileUrl 예시: https://bucket-name.s3.region.amazonaws.com/artworks/uuid-name.jpg
            // 여기서 "artworks/uuid-name.jpg" 부분(Key)만 추출해야 합니다.
            String key = fileUrl.substring(fileUrl.lastIndexOf("artworks/"));

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            System.out.println("S3 파일 삭제 성공: " + key);
        } catch (Exception e) {
            System.err.println("S3 파일 삭제 실패: " + e.getMessage());
            // 삭제 실패 시 로그만 남기거나 예외 처리를 합니다.
        }
    }
}
