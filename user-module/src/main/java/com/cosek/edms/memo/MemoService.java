package com.cosek.edms.memo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    @Autowired
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    public Memo getMemoById(Long id) {
        return memoRepository.findById(id).orElse(null);
    }

    public Memo createMemo(Memo memo) {
        return memoRepository.save(memo);
    }

    public Memo updateMemo(Long id, Memo updatedMemo) {
        updatedMemo.setId(id);
        return memoRepository.save(updatedMemo);
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
}
