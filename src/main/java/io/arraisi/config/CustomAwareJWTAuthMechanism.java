package io.arraisi.config;

import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.smallrye.jwt.runtime.auth.JWTAuthMechanism;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.Set;

@Slf4j
@Alternative
@Priority(1)
@ApplicationScoped
public class CustomAwareJWTAuthMechanism implements HttpAuthenticationMechanism {
    private static final Logger LOG = LoggerFactory.getLogger(CustomAwareJWTAuthMechanism.class);

    @Inject
    JWTAuthMechanism delegate;

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        // do some custom action and delegate
        String path = context.request().path();
        Buffer body = context.getBody();
        Future<Buffer> body1 = context.request().body();
        String user = context.request().getParam("User");
        String userHead = context.request().getHeader("User");
        String userFormAtt = context.request().getFormAttribute("User");
        context.request().bodyHandler(buffer -> {
            log.info("body buffer: {}", buffer);
        });
        body1.map(buffer -> {
            log.info("buffer: {}", buffer);
            return buffer;
        });
        Uni<SecurityIdentity> authenticate = delegate.authenticate(context, identityProviderManager);
        authenticate.map(securityIdentity -> {
            log.info("security identity: {}", securityIdentity);
            return securityIdentity;
        });
        return authenticate;
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        return delegate.getChallenge(context);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return delegate.getCredentialTypes();
    }

    @Override
    public HttpCredentialTransport getCredentialTransport() {
        return delegate.getCredentialTransport();
    }
}
