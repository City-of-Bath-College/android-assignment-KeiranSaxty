package keiransaxty.trueorfalse;

/*
   Created by Keiran Saxty (2015)
   GitHub: https://github.com/KeiranSaxty
*/

public class HighScoreObject {

    private String playerName;
    private int playerScore;
    private long timeStamp;
    private String difficulty;

   public HighScoreObject()
    {
        //NOTE: PaperDB would only accept an object with a no-arg constructor
        // so the object's properties have been assigned in SetValues() procedure

    }

    //Class properties are assigned here
    public void SetValues(String _playerName, int _playerScore, long _timeStamp, String _difficulty)
    {
        if(_playerName != null){
            this.playerName = _playerName;
        }
        else {
            this.playerName = "Unnamed";
        }
        this.playerScore = _playerScore;
        this.timeStamp = _timeStamp;
        this.difficulty = _difficulty;
    }

    public String GetPlayerName()
    {
        return this.playerName;
    }

    public int GetPlayerScore()
    {
        return this.playerScore;
    }

    public long GetTimeStamp()
    {
        return this.timeStamp;
    }

    public String GetDifficulty() {
        return this.difficulty;
    }

}
