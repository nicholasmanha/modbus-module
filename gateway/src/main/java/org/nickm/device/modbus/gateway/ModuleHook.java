package org.nickm.device.modbus.gateway;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import org.nickm.device.modbus.gateway.settings.ModbusDeviceSettings;
import com.inductiveautomation.ignition.gateway.config.migration.ExtensionPointRecordMigrationStrategy;
import com.inductiveautomation.ignition.gateway.config.migration.IdbMigrationStrategy;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.opcua.server.api.AbstractDeviceModuleHook;
import com.inductiveautomation.ignition.gateway.opcua.server.api.DeviceExtensionPoint;
import com.inductiveautomation.ignition.gateway.opcua.server.api.DeviceSettingsRecord;
import java.util.List;

@SuppressWarnings("unused")
public class ModuleHook extends AbstractDeviceModuleHook {

  @Override
  public void setup(GatewayContext context) {
    super.setup(context);

    BundleUtil.get().addBundle(ModbusDevice.class);
  }

  @Override
  public void startup(LicenseState activationState) {
    super.startup(activationState);
  }

  @Override
  public void shutdown() {
    super.shutdown();

    BundleUtil.get().removeBundle(ModbusDevice.class);
  }

  // ExampleDeviceExtensionPoint is referring to the class
  // I think this function is used by the gateway to get its extension points?
  @Override
  protected List<DeviceExtensionPoint<?>> getDeviceExtensionPoints() {
    return List.of(new ModbusDeviceExtensionPoint());
  }

  @Override
  public List<IdbMigrationStrategy> getRecordMigrationStrategies() {
    @SuppressWarnings("deprecation")
    var strategy =
            ExtensionPointRecordMigrationStrategy.newBuilder(ModbusDeviceExtensionPoint.TYPE_ID)
                    .resourceType(DeviceExtensionPoint.DEVICE_RESOURCE_TYPE)
                    .profileMeta(DeviceSettingsRecord.META)
                    .settingsRecordForeignKey(ModbusDeviceSettings.DEVICE_SETTINGS)
                    .settingsMeta(ModbusDeviceSettings.META)
                    .settingsEncoder(
                            b -> b.withCustomFieldName(ModbusDeviceSettings.TAG_COUNT, "general.tagCount"))
                    .build();

    return List.of(strategy);
  }
}