package biocomp.hubitatCi.deviceMetadata

import biocomp.hubitatCi.emulation.deviceApi.DeviceDefinition
import biocomp.hubitatCi.emulation.deviceApi.DeviceExecutor
import biocomp.hubitatCi.emulation.deviceApi.DeviceMetadata
import biocomp.hubitatCi.emulation.deviceApi.DeviceMetadataSource
import biocomp.hubitatCi.emulation.deviceApi.DeviceMultiAttributeTile
import biocomp.hubitatCi.emulation.deviceApi.DevicePreferences
import biocomp.hubitatCi.emulation.deviceApi.DeviceTile
import biocomp.hubitatCi.emulation.deviceApi.DeviceTiles
import groovy.transform.TypeChecked

@TypeChecked
class DeviceMetadataReader implements DeviceMetadataSource
{
    DeviceMetadataReader(DeviceExecutor delegate) {
        this.delegate = delegate
    }

    @Override
    void capability(String capabilityName) {

    }

    @Override
    void attribute(String attributeName, String attributeType, List<String> possibleValues) {

    }

    @Override
    void command(String commandName, List parameterTypes) {

    }

    @Override
    void fingerprint(Map options) {

    }

    @Override
    def input(Map options, String name, String type) {
        return null
    }

    @Override
    void main(String tileTitle) {

    }

    @Override
    void main(List multipleTileTitles) {

    }

    @Override
    void details(String tileTitle) {

    }

    @Override
    void details(List multipleTileTitles) {

    }

    @Override
    void standardTile(
            Object options, String name, String associatedAttributeName, @DelegatesTo(DeviceTile) Closure makeContents)
    {

    }

    @Override
    void valueTile(
            Map options, String name, String associatedAttributeName, @DelegatesTo(DeviceTile) Closure makeContents)
    {

    }

    @Override
    void controlTile(
            Map options, String name, String associatedAttributeName, @DelegatesTo(DeviceTile) Closure makeContents)
    {

    }

    @Override
    void multiAttributeTile(Map options, @DelegatesTo(DeviceMultiAttributeTile) Closure makeContents) {

    }

    @Override
    void tileAttribute(Map options, String associatedAttributeName, Closure makeContents) {

    }

    @Override
    void attributeState(Map options, String stateNameOrAttributeNameOrValue) {

    }

    @Override
    void state(Map options, String stateNameOrAttributeNameOrValue) {

    }

    @Override
    void definition(Map options, @DelegatesTo(DeviceDefinition) Closure makeContents) {

    }

    @Override
    void preferences(@DelegatesTo(DevicePreferences) Closure makeContents) {

    }

    @Override
    void tiles(Map options, @DelegatesTo(DeviceTiles) Closure makeContents) {

    }

    @Override
    void metadata(@DelegatesTo(DeviceMetadata) Closure makeContents) {

    }

    @Delegate
    private final DeviceExecutor delegate
}

