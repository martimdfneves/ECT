JedecChain;
	FileRevision(JESD32A);
	DefaultMfr(6E);
	P ActionCode(Cfg)
	Device PartName(EP4CE115F29) Path("./output_files/") File("XXX_PROJECT_NAME_XXX.sof") MfrSpec(OpMask(1));
ChainEnd;

AlteraBegin;
	ChainType(JTAG);
AlteraEnd;
