package com.example.ejournal.Controllers;

import com.example.ejournal.Models.Absenteeism;
import com.example.ejournal.Models.Mark;
import com.example.ejournal.Models.Schedule;
import com.example.ejournal.Models.User;
import com.example.ejournal.Repositories.AbsenteeismRepository;
import com.example.ejournal.Repositories.MarkRepository;
import com.example.ejournal.Repositories.ScheduleRepository;
import com.example.ejournal.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PrefectController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AbsenteeismRepository absenteeismRepository;

    @GetMapping
    public String showTeacherSchedule(@RequestParam("userId") Integer userId, Model model) {
        if (userId == null) {
            return "redirect:/";
        }

        User user = userRepository.findById(Long.valueOf(userId)).orElse(null);

        if (user == null || !user.getRole().equals("TEACHER")) {
            return "redirect:/";
        }

        List<Schedule> schedule = scheduleRepository.findByTeacherName(user.getName());
        model.addAttribute("schedule", schedule);
        model.addAttribute("userId", userId);
        return "teacher-schedule";
    }



    @GetMapping("/setabsenteeism")
    public String setAbsenteeismPage(Model model) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<User> students = userRepository.findByRole("student");
        model.addAttribute("schedules", schedules);
        model.addAttribute("students", students);
        return "setabsenteeism";
    }


    @PostMapping("/setabsenteeism")
    public String setAbsenteeism(@RequestParam Long schedule, @RequestParam Long student) {
        Schedule selectedSchedule = scheduleRepository.findById(schedule).orElse(null);
        User selectedStudent = userRepository.findById(student).orElse(null);

        if (selectedSchedule != null && selectedStudent != null) {
            Absenteeism newAbsenteeism = new Absenteeism();
            newAbsenteeism.setSubject(selectedSchedule.getSubject());
            newAbsenteeism.setTime(selectedSchedule.getTime());
            newAbsenteeism.setDate(selectedSchedule.getDayOfWeek());
            newAbsenteeism.setUserId(selectedStudent.getId().intValue());

            absenteeismRepository.save(newAbsenteeism);
        }

        return "redirect:/setabsenteeism";
    }

}