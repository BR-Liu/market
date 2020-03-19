package com.brliu.event;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CleanCacheEventListener {

    private final RedissonClient redissonClientSingle;

    @TransactionalEventListener(value = UpdateDBEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void onUpdateDB(String key) {
        if (!redissonClientSingle.getBucket(key).delete()) {
            // TODO: 2020/3/19 如果清缓存失败，写入队列异步执行
        }
    }
}
