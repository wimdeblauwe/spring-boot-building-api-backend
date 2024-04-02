package com.example.copsboot.report.web;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;

import java.util.Collections;
import java.util.List;

public class KeycloakAdminClientFacade {
    private final Keycloak keycloak;

    public KeycloakAdminClientFacade(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void createRealm(String realmName) {
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(realmName);
        realmRepresentation.setEnabled(true);
        RealmsResource realmsResource = keycloak.realms();
        realmsResource.create(realmRepresentation);
    }

    public void createRealmRole(String realmName, String roleName) {
        RealmResource copsbootRealm = keycloak.realm(realmName);
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        copsbootRealm.roles().create(roleRepresentation);
    }

    public void createUser(String realmName, String username, String password, String roleName) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(username);
        userRepresentation.setEnabled(true);
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        RealmResource realmResource = keycloak.realm(realmName);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(userRepresentation);
        String userId = CreatedResponseUtil.getCreatedId(response);

        UserResource userResource = usersResource.get(userId);

        userResource.resetPassword(credentialRepresentation);
        RoleRepresentation roleRepresentation = realmResource.roles().get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    public String createClient(String realmName, String clientId1) {
        RealmResource realmResource = keycloak.realm(realmName);
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(clientId1);
        clientRepresentation.setDirectAccessGrantsEnabled(true);
        Response response = realmResource.clients().create(clientRepresentation);
        String clientId = CreatedResponseUtil.getCreatedId(response);
        ClientResource clientResource = realmResource.clients().get(clientId);
        CredentialRepresentation secret = clientResource.getSecret();
        return secret.getValue();
    }
}
