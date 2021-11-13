package main.java.RaffleComponent;

import main.java.RaffleComponent.OrganizerRaffleEntity;

import java.time.LocalDate;
import java.util.ArrayList;

// the use case for when a raffle organizer wants to decide the winners of a raffle
public class RaffleWinnerGeneratorUseCase {

//    private final ArrayList<String> participantIdList;
//    private final int numberOfWinners;
//    private final ArrayList<Object> orgRaffleInfo;
    private ArrayList<Object> orgRaffleInfo;
    private OrganizerRaffleEntity orgRaffle;

    public RaffleWinnerGeneratorUseCase(String raffleId, ArrayList<Object> orgRaffleDetails) {

        // todo uncomment: this.orgRaffleInfo = DataAccess.getOrganizerRaffleById()
//        this.orgRaffleInfo = orgRaffleDetails;
        this.orgRaffle = new OrganizerRaffleEntity((String)this.orgRaffleInfo.get(0),
                (Integer)this.orgRaffleInfo.get(1), (LocalDate)this.orgRaffleInfo.get(3));
        this.orgRaffle.setRaffleId(raffleId);
        this.orgRaffle.setParticipantIdList((ArrayList<String>)this.orgRaffleInfo.get(5));

    }

    public ArrayList<String> updateRaffleWinners(){
        this.orgRaffle.setWinnerList(this.generateWinners());
        // todo uncomment: DataAccess.uploadModifiedOrgRaffle(this.orgRaffle)
        return this.orgRaffle.getWinnerList();
    }

    public ArrayList<String> generateWinners(){
        int i;
        ArrayList<String> winnersSoFar = new ArrayList<>();
        ArrayList<Integer> winningNumsSoFar = new ArrayList<>();

        for (i = 0; i < this.orgRaffle.getNumberOfWinners(); i++) {
            int winningEntry = calculateWinningEntry(winningNumsSoFar);
            winningNumsSoFar.add(winningEntry);
            winnersSoFar.add(this.orgRaffle.getParticipantIdList().get(winningEntry));  // winningEntry is the index
        }

        return winnersSoFar;  // returns arrayList of userId strings
    }

    public int calculateWinningEntry(ArrayList<Integer> winningNumsSoFar){

        int winningEntry = (int)(this.orgRaffle.getParticipantIdList().size() * Math.random());

        while (winningNumsSoFar.contains(winningEntry)){
            winningEntry = (int)(this.orgRaffle.getParticipantIdList().size() * Math.random());  // recalculate
        }

        return winningEntry;
    }
}