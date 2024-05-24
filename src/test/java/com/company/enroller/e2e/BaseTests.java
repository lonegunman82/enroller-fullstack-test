package com.company.enroller.e2e;

import com.company.enroller.App;
import com.company.enroller.controllers.MeetingRestController;
import com.company.enroller.controllers.ParticipantRestController;
import com.company.enroller.model.Meeting;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class BaseTests {

    @Autowired
    private MeetingRestController meetingRestController;

//    private ParticipantRestController participantRestController;

    @BeforeAll
    void startSpringBootApp() {
//        App.main(new String[]{});


        Meeting meeting = new Meeting();
        meeting.setTitle("Spotkanie testowe");
        meeting.setDescription("ABC");
        System.out.println(meeting);
        this.meetingRestController.addMeeting(meeting);
    }



}
