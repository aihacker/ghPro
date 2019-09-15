package wxgh.param.party.vote;

import wxgh.entity.party.vote.VoteRecord;

import java.util.List;

public class VoteRecordParam {
    private Integer voteId;
    private List<VoteRecord> answer;

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public List<VoteRecord> getAnswer() {
        return answer;
    }

    public void setAnswer(List<VoteRecord> answer) {
        this.answer = answer;
    }
}
