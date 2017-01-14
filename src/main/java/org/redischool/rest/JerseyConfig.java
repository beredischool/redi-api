package org.redischool.rest;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Created by aurel on 14/01/17.
 */
@Component
@ApplicationPath("/api")
public class JerseyConfig {

    public JerseyConfig() {}
}
