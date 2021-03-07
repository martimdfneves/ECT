#include "grp_mksofs.h"

#include "core.h"
#include "bin_mksofs.h"

#include <inttypes.h>
#include <iostream>

namespace sofs19
{
    void grpComputeStructure(uint32_t ntotal, uint32_t & itotal, uint32_t & nbref)
    {
        soProbe(601, "%s(%u, %u, ...)\n", __FUNCTION__, ntotal, itotal);

        /* change the following line by your code */
	//binComputeStructure(ntotal, itotal, nbref);
	
	if(itotal==0){

           itotal=static_cast<int>(ntotal)/16;

        }
        while(itotal%IPB!=0){
               itotal+=1;
        }


        uint32_t inodes;
        uint32_t data;

        inodes = itotal / IPB;
        data=ntotal-3-inodes;
	nbref = 1;
        while(data > 255){
		nbref+=1;
		data-=256;
	}		
    }
};
