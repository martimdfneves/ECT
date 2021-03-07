#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "bin_mksofs.h"

#include <inttypes.h>
#include <string.h>

namespace sofs19
{
    void grpFillReferenceDataBlocks(uint32_t ntotal, uint32_t itotal, uint32_t nbref)
    {
        soProbe(605, "%s(%u, %u, %u)\n", __FUNCTION__, ntotal, itotal, nbref);
	
	uint32_t ni = itotal/IPB;
	uint32_t ndata = ntotal-ni-1;

	uint32_t data = ndata-1-nbref;
	if(data < 65) return;	
	uint32_t start = nbref+1+64;
	uint32_t end = ndata-1;

	uint32_t pos = 1;
	uint32_t error = 0;
	uint32_t b[RPB];

	while(!error){
		for(int i = 1; i<RPB; i++){
			if(!error){
				b[i] = start++;
				if(start > end){
					error = 1;
				}

			}
			else{
				b[i] = NullReference;
			}
		}
		pos++;
		if(error) b[0] = NullReference;
		else b[0] = pos;
		soWriteRawBlock(pos+ni, b);
	}	

        
	
        //return binFillReferenceDataBlocks(ntotal, itotal, nbref);
    }
};

