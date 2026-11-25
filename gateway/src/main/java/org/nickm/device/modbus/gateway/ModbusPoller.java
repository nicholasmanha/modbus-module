package org.nickm.device.modbus.gateway;

import com.digitalpetri.modbus.client.ModbusTcpClient;
import com.digitalpetri.modbus.tcp.client.NettyTcpClientTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ModbusPoller {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String host;
    private final int port;

    private ModbusTcpClient client;
    private AutoCloseable connection;
    private volatile String status = "stopped";

    public ModbusPoller(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            NettyTcpClientTransport transport = NettyTcpClientTransport.create(cfg -> {
                cfg.setHostname(host);
                cfg.setPort(port);
            });
            client = ModbusTcpClient.create(transport);
            connection = client.open();

            status = "Connected to " + host + ":" + port;
            logger.info("Connected to Modbus device at localhost");
        } catch (Exception e) {
            status = e.getMessage();
            logger.error("Failed to connect to Modbus device at " + host + ":" + port + ", " + e);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
            logger.info("Closed Modbus connection to localhost");
        } catch (Exception e) {
            logger.warn("Error closing Modbus connection", e);
        } finally {
            connection = null;
            client = null;
            status = "stopped";
        }
    }

    public String getStatus() {
        return status;
    }
}