
package com.kiet.hostelmanagement.controller;

import com.kiet.hostelmanagement.model.HostelStudent;
import com.kiet.hostelmanagement.repository.HostelStudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class HostelStudentController {

    @Autowired
    private HostelStudentRepository repository;

    // 1. Add Student
    @PostMapping
    public HostelStudent addStudent(@RequestBody HostelStudent student) {
        return repository.save(student);
    }

    // 2. Get All Students
    @GetMapping
    public List<HostelStudent> getAllStudents() {
        return repository.findAll();
    }

    // 3. Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<HostelStudent> getStudentById(@PathVariable int id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update Student
    @PutMapping("/{id}")
    public ResponseEntity<HostelStudent> updateStudent(
            @PathVariable int id,
            @RequestBody HostelStudent updatedStudent) {

        return repository.findById(id).map(student -> {

            student.setHostelName(updatedStudent.getHostelName());
            student.setRoomNumber(updatedStudent.getRoomNumber());
            student.setFee(updatedStudent.getFee());

            HostelStudent savedStudent = repository.save(student);

            return ResponseEntity.ok(savedStudent);

        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Student deleted successfully!");
        }

        return ResponseEntity.status(404)
                .body("Student not found!");
    }
}
