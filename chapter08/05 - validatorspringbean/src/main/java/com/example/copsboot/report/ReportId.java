package com.example.copsboot.report;

import com.example.orm.jpa.AbstractEntityId;
import com.example.util.ArtifactForFramework;

import java.util.UUID;

public class ReportId extends AbstractEntityId<UUID> {
    @ArtifactForFramework
    protected ReportId() {
    }

    public ReportId(UUID id) {
        super(id);
    }
}
