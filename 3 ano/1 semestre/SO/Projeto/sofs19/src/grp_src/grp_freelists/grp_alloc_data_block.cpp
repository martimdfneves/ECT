/*
 *  \author António Rui Borges - 2012-2015
 *  \authur Artur Pereira - 2009-2019
 */

#include "grp_freelists.h"

#include <stdio.h>
#include <errno.h>
#include <inttypes.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>

#include "core.h"
#include "dal.h"
#include "freelists.h"
#include "bin_freelists.h"
#include "exception.h"

namespace sofs19
{
    uint32_t grpAllocDataBlock()
    {
        soProbe(441, "%s()\n", __FUNCTION__);

        /* change the following line by your code */
        /*
        SOSuperBlock *sb = soGetSuperBlockPointer();
            
        if(sb->head_cache.idx == TAIL_CACHE_SIZE){
            soReplenishHeadCache();
        }

        if(sb->head_cache.idx == NullReference){
            throw SOException(ENOSPC, __FUNCTION__);
        }

        uint32_t index = sb->head_cache.idx; 
        uint32_t ref = sb->head_cache.ref[index];
        sb->head_cache.ref[index] = NullReference;

        sb->head_cache.idx++; //incremento do idx
        sb->dz_free--; //decrementar nº de blocos livres
            
        soSaveSuperBlock();
			
        return ref;*/
        return binAllocDataBlock();
    }
};
