package com.cosek.edms.memo;

import com.cosek.edms.memo.Models.MemoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/memos")
public class MemoController {

    private final MemoService memoService;

    @Autowired
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Memo>> getAllMemos() {
        List<Memo> memos = memoService.getAllMemos();
        return ResponseEntity.ok(memos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Memo> getMemoById(@PathVariable Long id) {
        Memo memo = memoService.getMemoById(id);
        return memo != null ? ResponseEntity.ok(memo) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Memo> createMemo(@RequestBody MemoRequest memoRequest) {
        Memo memo = Memo.builder()
                .activityName(memoRequest.getActivityName())
                .preparedBy(memoRequest.getPreparedBy())
                .activityPurpose(memoRequest.getActivityPurpose())
                .activitySummary(memoRequest.getActivitySummary())
                .quantity(memoRequest.getQuantity())
                .duration(memoRequest.getDuration())
                .activityCost(memoRequest.getActivityCost())
                .date(memoRequest.getDate())
                .build();

        Memo createdMemo = memoService.createMemo(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMemo);
    }


    @PutMapping("update/{id}")
    public ResponseEntity<Memo> updateMemo(@PathVariable Long id, @RequestBody Memo memo) {
        Memo updatedMemo = memoService.updateMemo(id, memo);
        return ResponseEntity.ok(updatedMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return ResponseEntity.noContent().build();
    }
}
