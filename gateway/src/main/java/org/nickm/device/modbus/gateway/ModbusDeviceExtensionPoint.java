package org.nickm.device.modbus.gateway;

import com.inductiveautomation.ignition.gateway.config.ValidationErrors.Builder;
import com.inductiveautomation.ignition.gateway.dataroutes.openapi.SchemaUtil;
import com.inductiveautomation.ignition.gateway.opcua.server.api.Device;
import com.inductiveautomation.ignition.gateway.opcua.server.api.DeviceContext;
import com.inductiveautomation.ignition.gateway.opcua.server.api.DeviceExtensionPoint;
import com.inductiveautomation.ignition.gateway.opcua.server.api.DeviceProfileConfig;
import com.inductiveautomation.ignition.gateway.web.nav.ExtensionPointResourceForm;
import com.inductiveautomation.ignition.gateway.web.nav.WebUiComponent;
import java.util.Optional;
import java.util.Set;

// the extension point used in the module hook getDeviceExtensionPoints method
// <ExampleDeviceConfig> is a "type" parameter, which tells the class what type it's working with. e.g. List<String> names;
public class ModbusDeviceExtensionPoint extends DeviceExtensionPoint<ModbusDeviceConfig> {

  public static final String TYPE_ID = "ia.io.ExampleDevice";

  public ModbusDeviceExtensionPoint() {
    super(
            TYPE_ID,
            "ExampleDevice.Meta.DisplayName",
            "ExampleDevice.Meta.Description",
            ModbusDeviceConfig.class);
  }

  @Override
  protected Device createDevice(
          DeviceContext context, DeviceProfileConfig profileConfig, ModbusDeviceConfig deviceConfig) {

    return new ModbusDevice(context, deviceConfig);
  }

  @Override
  public Optional<WebUiComponent> getWebUiComponent(ComponentType type) {
    return Optional.of(
            new ExtensionPointResourceForm(
                    DeviceExtensionPoint.DEVICE_RESOURCE_TYPE,
                    "Device Connection",
                    TYPE_ID,
                    SchemaUtil.fromType(DeviceProfileConfig.class),
                    SchemaUtil.fromType(ModbusDeviceConfig.class),
                    Set.of()));
  }

  @Override
  protected void validate(ModbusDeviceConfig config, Builder errors) {
    errors.check(config.general().tagCount() > 0, "Tag Count must be positive");
  }
}