# Keycloak no Kubernetes

Este diretório contém os manifests para rodar o Keycloak e seu banco Postgres no Kubernetes, com importação automática do realm ms-ticket.

## Como aplicar

1. Gere o ConfigMap com o realm exportado:

   ```bash
   kubectl create configmap keycloak-realm-export \
     --from-file=realm-export.json=./realm-export.json \
     -n <namespace>
   ```
   Ou edite o arquivo `keycloak-realm-export-configmap.yaml` para embutir o JSON em base64.

2. Aplique os recursos:

   ```bash
   kubectl apply -k .
   ```

3. Acesse o Keycloak:
   - URL: http://<cluster-ip>:8080/
   - Admin: admin / admin
   - Realm: ms-ticket

4. O serviço ms-ticket pode ser configurado para usar o endpoint OIDC do Keycloak:
   - issuer-uri: http://keycloak:8080/realms/ms-ticket
   - client-id: ms-ticket
   - client-secret: secret

## Observações
- O banco de dados é efêmero (emptyDir). Para produção, use um PVC.
- O realm é importado apenas no primeiro start do Keycloak.
