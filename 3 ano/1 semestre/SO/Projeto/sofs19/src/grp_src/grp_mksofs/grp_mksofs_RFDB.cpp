#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "bin_mksofs.h"

#include <string.h>
#include <inttypes.h>

namespace sofs19
{
    void grpResetFreeDataBlocks(uint32_t ntotal, uint32_t itotal, uint32_t nbref)
    {
        soProbe(607, "%s(%u, %u, %u)\n", __FUNCTION__, ntotal, itotal, nbref);

		
	uint32_t ni = itotal/IPB;
	uint32_t d = ntotal-2-ni-nbref;
	uint32_t b[RPB];
	
	for(int i = 0; i<RPB; i++)
	      	b[i] = 0;

	for(int i = 0; i<d; i++)
		soWriteRawBlock(2+i+ni+nbref, b);

			
	/* change the following line by your code */
	//binResetFreeDataBlocks(ntotal, itotal, nbref);
    }
};

