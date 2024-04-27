package com.yumeinaruu.iis.service;

import com.yumeinaruu.iis.model.Marks;
import com.yumeinaruu.iis.model.dto.marks.MarksCreateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksMarkUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksSubjectUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksUpdateDto;
import com.yumeinaruu.iis.model.dto.marks.MarksUserUpdateDto;
import com.yumeinaruu.iis.repository.MarksRepository;
import com.yumeinaruu.iis.repository.SubjectRepository;
import com.yumeinaruu.iis.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarksService {
    private final MarksRepository marksRepository;
    private final SubjectRepository subjectRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public MarksService(MarksRepository marksRepository, SubjectRepository subjectRepository, UsersRepository usersRepository) {
        this.marksRepository = marksRepository;
        this.subjectRepository = subjectRepository;
        this.usersRepository = usersRepository;
    }

    public List<Marks> getAllMarks() {
        return marksRepository.findAll();
    }

    public Optional<Marks> getMarkById(Long id) {
        return marksRepository.findById(id);
    }

    public List<Marks> getMarksAscending() {
        return marksRepository.findAll(Sort.by("mark").ascending());
    }

    public List<Marks> getMarksDescending() {
        return marksRepository.findAll(Sort.by("mark").descending());
    }

    public Boolean createMark(MarksCreateDto marksCreateDto) {
        Marks marks = new Marks();
        marks.setMark(marksCreateDto.getMark());
        if (subjectRepository.findByName(marksCreateDto.getSubject()).isPresent()) {
            marks.setSubjectId(subjectRepository.findByName(marksCreateDto.getSubject()).get().getId());
        }
        if(usersRepository.findByUsername(marksCreateDto.getUser()).isPresent()){
            marks.setUserId(usersRepository.findByUsername(marksCreateDto.getUser()).get().getId());
        }
        Marks savedMarks = marksRepository.save(marks);
        return getMarkById(savedMarks.getId()).isPresent();
    }

    public Boolean updateMark(MarksUpdateDto marksUpdateDto) {
        Optional<Marks> optionalMarks = marksRepository.findById(marksUpdateDto.getId());
        if (optionalMarks.isPresent()) {
            Marks marks = optionalMarks.get();
            marks.setMark(marksUpdateDto.getMark());
            if (subjectRepository.findByName(marksUpdateDto.getSubject()).isPresent()) {
                marks.setSubjectId(subjectRepository.findByName(marksUpdateDto.getSubject()).get().getId());
            }
            if(usersRepository.findByUsername(marksUpdateDto.getUser()).isPresent()){
                marks.setUserId(usersRepository.findByUsername(marksUpdateDto.getUser()).get().getId());
            }
            Marks savedMarks = marksRepository.save(marks);
            return getMarkById(savedMarks.getId()).isPresent();
        }
        return false;
    }

    public Boolean updateMarksMark(MarksMarkUpdateDto marksMarkUpdateDto) {
        Optional<Marks> optionalMarks = marksRepository.findById(marksMarkUpdateDto.getId());
        if (optionalMarks.isPresent()) {
            Marks marks = optionalMarks.get();
            marks.setMark(marksMarkUpdateDto.getMark());
            Marks savedMarks = marksRepository.save(marks);
            return getMarkById(savedMarks.getId()).isPresent();
        }
        return false;
    }

    public Boolean updateMarksSubject(MarksSubjectUpdateDto marksSubjectUpdateDto) {
        Optional<Marks> optionalMarks = marksRepository.findById(marksSubjectUpdateDto.getId());
        if (optionalMarks.isPresent()) {
            Marks marks = optionalMarks.get();
            if(subjectRepository.findByName(marksSubjectUpdateDto.getSubject()).isPresent()){
                marks.setSubjectId(subjectRepository.findByName(marksSubjectUpdateDto.getSubject()).get().getId());
            }
            Marks savedMarks = marksRepository.save(marks);
            return getMarkById(savedMarks.getId()).isPresent();
        }
        return false;
    }

    public Boolean updateMarksUser(MarksUserUpdateDto marksUserUpdateDto) {
        Optional<Marks> optionalMarks = marksRepository.findById(marksUserUpdateDto.getId());
        if (optionalMarks.isPresent()) {
            Marks marks = optionalMarks.get();
            if(usersRepository.findByUsername(marksUserUpdateDto.getUser()).isPresent()){
                marks.setUserId(usersRepository.findByUsername(marksUserUpdateDto.getUser()).get().getId());
            }
            Marks savedMarks = marksRepository.save(marks);
            return getMarkById(savedMarks.getId()).isPresent();
        }
        return false;
    }

    public Boolean deleteMarkById(Long id) {
        Optional<Marks> optionalMarks = marksRepository.findById(id);
        if (optionalMarks.isEmpty()) {
            return false;
        }
        marksRepository.delete(optionalMarks.get());
        return true;
    }
}
