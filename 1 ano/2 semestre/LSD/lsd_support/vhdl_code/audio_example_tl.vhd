----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE), VHDL 2008
--
-- Example of utilization of the audio entities, of the sin_function entity, and of the blop_sound_rom entity. This example does the following:
-- * a 1kHz tone is played continuously on the left audio channel
-- * a "blop" sound is played on the right channel whenever key(0) is pressed
-- The ADC part of the audio entities is not used in this example.
--
-- More advanced examples can be found in the demonstrations directory.
--
-- The audio example (under GNU/Linux, create and compile it using the command "make audio_example") uses the following files:
-- * vhdl_code/audio_example_tl.vhd
-- * vhdl_code/audio.vhd
-- * vhdl_code/clock_generator.vhd
-- * vhdl_code/sin_function.vhd
-- * vhdl_code/blop_sound_rom.vhd
-- * vhdl_code/debouncer.vhd
--

library IEEE;
use     IEEE.STD_LOGIC_1164.ALL;
use     IEEE.NUMERIC_STD.ALL;

entity audio_example_tl is
  generic
  (
    CLOCK_50_FREQUENCY : real := 50.0e6 -- frequency of the clock_50 input signal (must be 50MHz)
  );
  port
  (
    clock_50 : in std_logic;

    key : in std_logic_vector(0 downto 0);

    i2c_sclk    : inout std_logic;
    i2c_sdat    : inout std_logic;
    aud_xck     : out   std_logic;
    aud_bclk    : in    std_logic;
    aud_adclrck : in    std_logic;
    aud_adcdat  : in    std_logic;
    aud_dacdat  : out   std_logic
  );
end audio_example_tl;

architecture example of audio_example_tl is
  --
  -- The master clock (in this case, this is just clock_50)
  --
  constant CLOCK_FREQUENCY : real := CLOCK_50_FREQUENCY;
  signal clock : std_logic;
  --
  -- audio
  --
  signal audio_pulse         : std_logic;
  signal to_left,to_right    : std_logic_vector(15 downto 0);
  --
  -- 1 kHz pure tone generator
  --
  constant SIN_ARG_INCREMENT : integer := integer(2.0**18*1.0e3/48.0e3); -- one period corresponds to 2**18, frequency of 1.0e3, sampling frequency of 48.0e3
  signal sin_arg             : std_logic_vector(17 downto 0); -- the argument to the sin_function entity (2*pi corresponds to 2**18)
  signal sin_val             : std_logic_vector(15 downto 0); -- the sinusoidal sound sample (pure tone sound sample)
  --
  -- Blop sound generator
  --
  signal blop_addr         : unsigned(11 downto 0) := X"FFF"; -- when the address is at the end, the blop sound generator is inactive
  signal blop_val          : std_logic_vector(15 downto 0);   -- the blop sound sample
  signal key_pressed_pulse : std_logic;                       -- the blop sound is played whenever key(0) is pressed
begin
  --
  -- The master clock (in this case there is no need to change the clock frequency, so our master clock is just clock_50)
  --
  clock <= clock_50;
  --
  -- Audio entities
  --
  AUDIO_C : entity work.audio_controller(smart)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock => clock,
      reset => '0',
      i2c_sclk => i2c_sclk,
      i2c_sdat => i2c_sdat,
      use_line_in => '1',      -- although in this example we do not use the ADC side of the audio codec,
      line_in_gain => "00000", -- we set here use_line_in to '1', because otherwise the audio_pulse signal,
      use_mic => '0',          -- which controls the audio loop, is not generated (that is a deficiency
      mic_boost => '0',        -- of the current audio implementation)
      line_bypass => '0',
      mic_bypass => '0',
      volume => "1101000"      -- a relatively low volume
    );
  AUDIO_IO : entity work.audio_io(audio_data_transfer)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY
    )
    port map
    (
      clock    => clock,
      clock_50 => clock_50,
      aud_xck     => aud_xck,
      aud_bclk    => aud_bclk,
      aud_adclrck => aud_adclrck,
      aud_adcdat  => aud_adcdat,
      aud_dacdat  => aud_dacdat,
      from_left  => open,        -- the ADC signals are not needed, so we leave them open
      from_right => open,        -- the ADC signals are not needed, so we leave them open
      valid      => audio_pulse, -- this signal controls the pace of the audio loop
      to_left    => to_left,
      to_right   => to_right
    );
  --
  -- Pure tone generator ROM interface
  --
  -- It takes 5 clock cycles for sin_val to reflect a change of value of sin_arg. Since the value of sin_val will be used much later on, that does not mater.
  --
  PURE_TONE : entity work.sin_function(pipelined)
    port map
    (
      clock => clock,
      arg_0 => sin_arg,
      sin_5 => sin_val
    );
  --
  -- Blop sound ROM interface. Again, a delay of one clock cycle (from blop_addr to blop_val) is irrelevant here.
  --
  BLOP : entity work.blop_sound_rom(waveform_data)
    port map
    (
      clock => clock,
      addr0_0 => std_logic_vector(blop_addr),
      data0_1 => blop_val,
      addr1_0 => X"---", -- not used (but input signals cannot be left open, so we set it to "don't care")
      data1_1 => open    -- not used
    );
  --
  -- Blop sound trigger
  --
  BLOP_TRIGGER : entity work.debouncer(fancy)
    generic map
    (
      CLOCK_FREQUENCY => CLOCK_FREQUENCY,
      WINDOW_DURATION => 20.0e-6,  -- 20μs
      DELAY_DURATION  => 20.0e-3,  -- 20ms
      INITIAL_LEVEL   => '1'       -- assume the key is initially not pressed (not really relevant, because of the global reset signal)
    )
    port map
    (
      clock             => clock,
      reset             => '0',
      dirty             => key(0), -- key(0) generates the counter reset event
      clean             => open,   -- not used, so leave it open
      zero_to_one_pulse => open,   -- not used, so leave it open
      one_to_zero_pulse => key_pressed_pulse
    );
  --
  -- Audio loop
  --
  to_left  <= sin_val;  -- pure tone of the left channel
  to_right <= blop_val; -- blop sound on the right channel
  AUDIO_LOOP: process(clock) is
  begin
    if rising_edge(clock) then
      if audio_pulse = '1' then
        --
        -- Pure tone
        --
        sin_arg <= std_logic_vector(unsigned(sin_arg)+SIN_ARG_INCREMENT); -- this changes 48000 times per second
        --
        -- Blop sound
        --
        if blop_addr /= X"FFF" then
          blop_addr <= blop_addr+1; -- continue to play the blop sound
        end if;
      end if;
      --
      -- Blop sound trigger (this has to be placed outside of the audio_pulse = '1' test, because the key_pressed_pulse only lasts one clock cycle)
      --
      if key_pressed_pulse = '1' then
        blop_addr <= X"000"; -- play blop sound from the beginning
      end if;
    end if;
  end process;
end example;
