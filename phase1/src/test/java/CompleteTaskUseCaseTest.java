package test.java;

import main.java.RaffleComponent.CompleteTaskUseCase;
import main.java.RaffleComponent.OrganizerRaffleEntity;
import main.java.RaffleComponent.RaffleEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CompleteTaskUseCaseTest {

    // todo: test that you return the correct completedTaskIds when completing a valid taskId and when taskId is bad

    String correctTaskCompletedId;
    String wrongTaskCompletedId;
    CompleteTaskUseCase correctRaffleManager;
    CompleteTaskUseCase wrongRaffleManager;
    RaffleEntity ptcRaffle;
    OrganizerRaffleEntity orgRaffle;
    UseCaseTestHelpers helper;

    @Before
    public void setUp() throws Exception{
        correctTaskCompletedId = "T1001";
        wrongTaskCompletedId = "T1002";

        orgRaffle = new OrganizerRaffleEntity("TestRaffle",
                2, LocalDate.of(2021, 12, 25));
        orgRaffle.setRaffleId("R1001");
        orgRaffle.setRaffleRules("Some rules POGCHAMP");
        // "T1002" already in dummy database
        ArrayList<String> presentTaskIds = new ArrayList<>();
        presentTaskIds.add(correctTaskCompletedId);
        presentTaskIds.add("T1004");
        presentTaskIds.add("T1005");
        orgRaffle.setTaskIdList(presentTaskIds);
        // ptcIdList has the participant completing the task, but that's irrelevant to this test

        ptcRaffle = new RaffleEntity(orgRaffle.getRaffleName(), orgRaffle.getNumberOfWinners(),
                orgRaffle.getEndDate());
        ptcRaffle.setRaffleRules(orgRaffle.getRaffleRules());
        ptcRaffle.setTaskIdList(orgRaffle.getTaskIdList());
        String ptcRaffleId = "UserName4587:" + orgRaffle.getRaffleId();
        ptcRaffle.setRaffleId(ptcRaffleId);

        correctRaffleManager = new CompleteTaskUseCase(correctTaskCompletedId, ptcRaffleId);
        correctRaffleManager.setPtcRaffle(ptcRaffle);
        correctRaffleManager.setOrgRaffleInfo(helper.setupDummyOrgRaffleInfo(orgRaffle));

        wrongRaffleManager = new CompleteTaskUseCase(wrongTaskCompletedId, ptcRaffleId);
        wrongRaffleManager.setPtcRaffle(ptcRaffle);
        wrongRaffleManager.setOrgRaffleInfo(helper.setupDummyOrgRaffleInfo(orgRaffle));
    }

    @Test(timeout = 60)
    public void TestCorrectCompletedTasksReturned(){
        ArrayList<String> completedTasksActual = correctRaffleManager.completeTask();
        ArrayList<String> completedTasksExpected = new ArrayList<>();
        completedTasksExpected.add(correctTaskCompletedId);
        assertEquals(completedTasksActual, completedTasksExpected);
    }

    public void TestWrongCompletedTaskReturned(){
        ArrayList<String> completedTasksActual = wrongRaffleManager.completeTask();
        ArrayList<String> completedTasksExpected = new ArrayList<>();  // no tasks from orgRaffle completed
        assertEquals(completedTasksActual, completedTasksExpected);
    }





}
