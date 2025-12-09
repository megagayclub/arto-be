package com.arto.arto.domain.inquiry_answers.repository;

import com.arto.arto.domain.admins.entity.AdminsEntity;
import com.arto.arto.domain.inquiry_answers.entity.InquiryAnswersEntity;
import com.arto.arto.domain.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryAnswersRepository extends JpaRepository<InquiryAnswersEntity, Long> {
}