#
# LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE)
#
# Master timing constraints
#
# Note: this file has to be adapted to what is actually needed by each project.
# This can be done by commenting the set_false_path lines corresponding to pins not used in the project.
#


#
# Do timing analysis for a very pessimistic case: slow and very hot FPGA.
#
set_operating_conditions 7_slow_1200mv_85c


#
# Identify the 50MHz clock and any other clocks obtained from it.
#
set_time_format -decimal_places 3 -unit ns
create_clock -name clock_50 -period 20.000 [ get_ports clock_50 ]
derive_pll_clocks
derive_clock_uncertainty


#
# "Slow" I/O pins (remove timing constraints for some FPGA pins)
#
# We don't care about setup and hold times concerning "slow" I/O pins.
#
# When input (to the FPGA) signals are used in sequential logic, it
# is IMPERATIVE that the first thing that is done to them inside the
# FPGA be to pass them through registers.
#
# For slow I/O signals, outputs do not need to pass through registers
# (but there is no harm if they do so).
#

# keys
set_false_path -from [ get_ports key[*] ] -to [ get_clocks * ]

# switches
set_false_path -from [ get_ports sw[*] ] -to [ get_clocks * ]

# red leds
set_false_path -from [ get_clocks * ] -to [ get_ports ledr[*] ]

# green leds
set_false_path -from [ get_clocks * ] -to [ get_ports ledg[*] ]

# 7-segment displays
set_false_path -from [ get_clocks * ] -to [ get_ports hex*[*] ]

# audio
set_false_path -from [ get_ports aud_* ] -to [ get_clocks * ]
set_false_path -from [ get_clocks * ] -to [ get_ports aud_* ]
set_false_path -from [ get_ports i2c_* ] -to [ get_clocks * ]
set_false_path -from [ get_clocks * ] -to [ get_ports i2c_* ]

# infrared
set_false_path -from [ get_ports irda_* ] -to [ get_clocks * ]

# lcd
set_false_path -from [ get_ports lcd_* ] -to [ get_clocks * ]
set_false_path -from [ get_clocks * ] -to [ get_ports lcd_* ]

# ps2
set_false_path -from [ get_ports ps2_* ] -to [ get_clocks * ]
set_false_path -from [ get_clocks * ] -to [ get_ports ps2_* ]

# rs232
set_false_path -from [ get_ports uart_* ] -to [ get_clocks * ]
set_false_path -from [ get_clocks * ] -to [ get_ports uart_* ]


#
# VGA I/O
#
# Given the inverted phase relationship between vga_clk and the rest of the VGA signals,
# and assuming that there are no electric signal propagation time mismatches of more
# than half the pixel clock period minus 2ns between the FPGA pins and the video DAC
# pins, there is no need to time-constrain the VGA signals.
#
set_false_path -from [ get_clocks * ] -to [ get_ports vga_* ]


#
# Static RAM
#
# The way the sram_controller entity works makes setup and hold times irrelevant
#
set_false_path -from [ get_ports sram_* ] -to [ get_clocks * ]
set_false_path -from [ get_clocks * ] -to [ get_ports sram_* ]
