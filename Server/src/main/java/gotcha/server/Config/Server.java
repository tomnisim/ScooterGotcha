package gotcha.server.Config;

public class Server {
        private String host;
        private String port;
        private String protocol;
        // <editor-fold desc="Accessor Methods">
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }
        // </editor-fold>

}
