package player;

public enum Ability {
	Temperature, Water, Oxygen,	Terraform_Rating,					// Parameter abilities
	Place_City, Place_Greenery, 									// General abilities
	Sell_Patent, Power_Plant, Asteroid, Aquifier, Greenery, City,	// Standard project abilities
	Convert_Greenery, Convert_Temperature,							// inventory abilities
	
	// Custom placement abilities
	Place_Noctis_City, Place_Phobos_Space_Haven, Place_Ganymede_Colony,		// designated cities
	Place_Mine, Place_Nuclear_Zone,											// custom tile placements
	Place_Volcano, Place_Restricted_Area, Place_Commercial_District, Place_Capital,
	Place_Ecological_Zone, Place_Industrial_Center, Place_Urbanized_Area,
	Place_Mohole_Area, Place_Flooding, Place_Blocker, Place_Volcano_City,
	Place_Natural_Preserve, Place_Research_Outpost, Place_Artificial_Lake,
	Place_Protected_Valley, Place_Mangrove,
	Water_On_Land, Land_On_Water,											// custom placements
	Plant_for_City, MegaCredit_for_City, Energy_for_City, 					// custom resource increases
	Energy_for_Power, Plant_for_Microbes, Plant_for_Plant,
	MegaCredit_for_Building, MegaCredit_for_Space, MegaCredit_for_Earth,
	MegaCredit_for_Earth2, TR_for_Jovian, MegaCredit_for_Opponent_Space,
	MegaCredit_for_All_Event,
	
	Plant_Or_Energy, Heat_for_MegaCredit, More_Plants,
	Sabotage, Hired_Raiders, Card1_of_3, Card2_of_4,
	
	Add2Microbe, Add3Microbe, AddAnimal, Add2Animal,
	Add4PlantOr2Animal, Add5PlantOr4Animal, Add3PlantOr2AnimalOr3Microbe, 
	AddCounter, RemoveAnimalOrPlant, DuplicateProduction, FundAward,
	Prelude, DrawPlantCards, DrawSpaceCards, DrawCounterCards,
	
	// Custom activated abilities
	Energy_to_Card, Energy_to_MegaCredit, Energy_to_MegaCredit_City,
	Energy_to_TR, Energy_to_Oxygen, Energy_to_Oxygen_Steel, 
	Energy_to_Oxygen_Steel2, Energy_to_Oxygen_Titanium,
	Steel_to_MegaCredit, Heat_to_TR, Draw_2_Cards,
	MegaCredit_to_Energy, MegaCredit_to_Heat, PlantSteel_to_MegaCredit,
	MegaCreditSteel_to_Water, MegaCreditTitanium_to_Water,
	PurchaseCard, MegaCredit_to_Card, MegaCredit_to_Steel,
	Add_Tardigrades, Add_GHGBacteria, Add_Regolith, Add_NitriteBacteria,
	Add_Fish, Add_Livestock, Add_Bird, Add_SmallAnimal, Add_Fleet,
	Add_Fungus, Add_ColdFungus, Add_Psychrophiles,
	Counter_to_TR, Counter_to_Oxygen, Counter_to_Temperature,
	Energy_to_Science, MegaCredit_Search_Microbe, StealAnimal, StealMicrobe,
	AddFloater, Heat_to_Resource,
	
	// Custom permanent abilities
	Global_Parameter, Global_ParameterTemp, Global_ParameterTemp2,
	GlobalDiscount, GlobalDiscount2, DiscountTemp2,
	SpaceDiscount, EarthDiscount, BlockedCounters, SpaceEventMegaCredit,
	EventMegaCredit, ScienceDrawCard, ScienceDiscardCard, Decomposers, 
	ViralEnhancers, DiscountProjects, DiscountTemp, Herbivores, ArcticAlgae,
	RoverConstruction, Pets, ImmigrantCity, ValuableResources, Psychrophiles,
	BuildingDiscount, ScienceDiscount,
	
	// Company abilities
	PayWithHeat, EventMegaCredit2, Credicor, Ecoline, Mining, Tharsis, Thorgate,
	UNMI, SaturnSystem, PointLuna, Robinson, Vitor, Celestic, Manutech, Viron,
	Polyphemos, Terralabs, OuterResearch,
	
	// Custom end-of-game abilities
	Jovian_Points, City_Points, Adjacent_Water_Points,
	PointPerCounter, PointPerCounter2, PointPerCounter3, PointPerCounter4,
	PointPer2Counter, PointIfCounter3,
	
	// Custom validation functions
	Has_Steel_Generation, Has_Titanium_Generation, Cities_Exist
}
