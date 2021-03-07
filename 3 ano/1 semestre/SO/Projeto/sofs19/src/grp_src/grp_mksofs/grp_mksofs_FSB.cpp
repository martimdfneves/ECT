#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "bin_mksofs.h"

#include <string.h>
#include <inttypes.h>

namespace sofs19
{
    void grpFillSuperBlock(const char *name, uint32_t ntotal, uint32_t itotal, uint32_t nbref)
    {
        soProbe(602, "%s(%s, %u, %u, %u)\n", __FUNCTION__, name, ntotal, itotal, nbref);
        
        /* change the following line by your code */
         
        SOSuperBlock sb;
        
        sb.magic = 0xFFFF;
        sb.version = VERSION_NUMBER;
        strncpy(sb.name,name,PARTITION_NAME_SIZE + 1);
        sb.mntstat = 1;
        sb.mntcnt = 0;
        sb.ntotal = ntotal;
        
        sb.it_size = itotal / IPB;
        sb.itotal = itotal;
        sb.ifree = itotal - 1;
        sb.ihead = 1;
        sb.itail = itotal - 1;
        
        sb.dz_start = sb.it_size + 1;
        sb.dz_total = ntotal - 1 - sb.it_size;
        sb.dz_free = ntotal - nbref;
        sb.head_blk = 1;
        sb.head_idx = 1;
        sb.tail_blk = nbref;
        sb.tail_idx = sb.dz_total - 65 - (RPB * (nbref - 1));
        
        
        for(int i = 0; i < HEAD_CACHE_SIZE; i++) {
            sb.head_cache.ref[i] = nbref + 1 + i;
			sb.head_cache.idx = 0;
		}
        
        for(int i = 0; i < TAIL_CACHE_SIZE; i++) {
            sb.tail_cache.ref[i] = NullReference;
			sb.tail_cache.idx = 0;
		}
		
		soWriteRawBlock(0, &sb);
        
	
        //binFillSuperBlock(name, ntotal, itotal, nbref);
    }
    


    
};

	

