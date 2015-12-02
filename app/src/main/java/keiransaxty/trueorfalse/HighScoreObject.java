package keiransaxty.trueorfalse;

public class HighScoreObject {

    private String playerName;
    private int playerScore;
    private long timeStamp;

   public HighScoreObject()
    {
        //NOTE: PaperDB would only accept an object with a no-arg constructor
        // so the object's properties have been assigned in SetValues() procedure

        this.playerName = playerName;
        this.playerScore = playerScore;
        this.timeStamp = timeStamp;
    }


    public void SetValues(String _playerName, int _playerScore, long _timeStamp)
    {
        if(_playerName != null){
            this.playerName = _playerName;
        }
        else {
            this.playerName = "Unnamed";
        }
        this.playerScore = _playerScore;
        this.timeStamp = _timeStamp;
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

}
