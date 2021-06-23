package nl.overheid.aerius.service.domain;

public enum ServiceSelectorName {
  HABITAT_TYPE("habitatTypeCode"),

  GOAL_HABITAT_TYPE("goalHabitatTypeCode"),

  SPECIES("speciesCode"),

  REHABILITATION_STRATEGY("rehabilitationStrategyCode"),

  NATURA_2000_AREA("natura2000AreaCode"),

  YEAR("year"),

  COUNTRY("countryCode"),

  ABROAD_SECTOR("abroadSectorCode"),

  SECTOR("sectorCode"),

  SECTORGROUP("sectorgroupCode"),

  OTHER_DEPOSITION_CODE("otherDepositionType"),

  NITROGEN_SOURCE("nitrogenSourceCode"),

  COMPARING_DATASET("comparingDataset"),

  SEGMENT_REGISTER("segmentRegister"),

  SEGMENT_MONITOR("segmentMonitor");

  private final String name;

  private ServiceSelectorName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}