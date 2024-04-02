package com.example.copsboot.user;

import com.example.orm.jpa.AbstractEntityId;

import java.util.UUID;

public class UserId extends AbstractEntityId<UUID> {

    protected UserId() { //<1>

    }

    public UserId(UUID id) { //<2>
        super(id);
    }
}
