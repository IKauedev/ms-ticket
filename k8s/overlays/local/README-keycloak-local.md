# Keycloak local no Kubernetes (overlay local)

Este overlay inclui Keycloak e seu banco Postgres para desenvolvimento local.

## Como usar

1. Gere o ConfigMap do realm:

   ```bash
   kubectl create configmap keycloak-realm-export \
     --from-file=realm-export.json=../../keycloak/realm-export.json \
     -n ms-ticket-local
   ```
   Ou edite o arquivo `keycloak-realm-export-configmap.yaml` para embutir o JSON.

2. Aplique o overlay local:

   ```bash
   kubectl apply -k .
   ```

3. Acesse o Keycloak em http://localhost:30881/
   - Admin: admin / admin
   - Realm: ms-ticket

4. Configure o ms-ticket para usar:
   - issuer-uri: http://keycloak:8080/realms/ms-ticket
   - client-id: ms-ticket
   - client-secret: secret

## Observações
- O banco do Keycloak é efêmero (emptyDir). Para persistência, adapte para usar PVC.
- O realm é importado apenas no primeiro start do Keycloak.
