package com.sanhang.springdatajpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.sanhang.springdatajpa.dao.StudentDao;
import com.sanhang.springdatajpa.model.QStudent;
import com.sanhang.springdatajpa.model.Student;
import com.sanhang.springdatajpa.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private StudentDao dao;
    
    public List<Student> search() {
    	QStudent qstudent = QStudent.student;
    	BooleanBuilder builder = new BooleanBuilder();
    	builder.and(qstudent.name.eq("小明"));
    	builder.and(qstudent.teacher.name.eq("张三"));
    	return dao.search(builder.getValue());
    }
    
}