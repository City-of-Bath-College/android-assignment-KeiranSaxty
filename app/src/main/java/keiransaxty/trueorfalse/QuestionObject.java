package keiransaxty.trueorfalse;

public class QuestionObject {

    private String questionText;
    private boolean answer;
    private int imageNo;

    public QuestionObject(String questionText, boolean answer, int imageNo)
    {
        this.questionText = questionText;
        this.answer = answer;
        this.imageNo = imageNo;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public boolean getAnswer()
    {
        return answer;
    }

    public int getImage()
    {
        return imageNo;
    }


}
