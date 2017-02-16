package org.redischool.services;

import java.util.UUID;

/**
 * Created by aurel on 16/01/17.
 */
public abstract class AbstractService {
    public UUID generateId() {
        return UUID.randomUUID();
    }
}
