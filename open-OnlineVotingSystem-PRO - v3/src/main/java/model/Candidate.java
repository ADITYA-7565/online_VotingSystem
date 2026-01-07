package model;

public class Candidate {

    private int id;
    private String name;
    private String party;
    private String photo;
    private int votes;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public int getVotes() { return votes; }
    public void setVotes(int votes) { this.votes = votes; }
}
