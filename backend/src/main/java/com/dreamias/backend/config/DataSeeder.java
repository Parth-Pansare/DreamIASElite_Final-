package com.dreamias.backend.config;

import com.dreamias.backend.entity.Subject;
import com.dreamias.backend.entity.Unit;
import com.dreamias.backend.entity.Topic;
import com.dreamias.backend.repository.SubjectRepository;
import com.dreamias.backend.repository.UnitRepository;
import com.dreamias.backend.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SubjectRepository subjectRepository;
    private final UnitRepository unitRepository;
    private final TopicRepository topicRepository;
    private final com.dreamias.backend.repository.UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    public DataSeeder(SubjectRepository subjectRepository, 
                      UnitRepository unitRepository, 
                      TopicRepository topicRepository,
                      com.dreamias.backend.repository.UserRepository userRepository,
                      org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.subjectRepository = subjectRepository;
        this.unitRepository = unitRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            seedAdmin();
        }
        if (subjectRepository.count() == 0) {
            seedData();
        }
    }

    private void seedAdmin() {
        com.dreamias.backend.entity.User admin = new com.dreamias.backend.entity.User();
        admin.setName("Admin User");
        admin.setEmail("admin@dreamias.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(com.dreamias.backend.entity.Role.ADMIN);
        userRepository.save(admin);
        System.out.println("--- Admin User Created: admin@dreamias.com / admin123 ---");
    }

    private void seedData() {
        Subject polity = new Subject();
        polity.setName("Indian Polity");
        polity.setDescription("Study of the Constitution and Political System of India.");
        polity = subjectRepository.save(polity);

        Unit unit1 = new Unit();
        unit1.setName("Constitutional Framework");
        unit1.setSubject(polity);
        unit1 = unitRepository.save(unit1);

        Topic topic1 = new Topic();
        topic1.setName("Fundamental Rights");
        topic1.setUnit(unit1);
        topicRepository.save(topic1);

        Topic topic2 = new Topic();
        topic2.setName("Directive Principles (DPSP)");
        topic2.setUnit(unit1);
        topicRepository.save(topic2);

        System.out.println("--- Database Seeded with Initial UPSC Content ---");
    }
}
