/*
 *  \author António Rui Borges - 2012-2015
 *  \authur Artur Pereira - 2016-2019
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

namespace sofs19
{
    void grpFreeDataBlock(uint32_t bn)
    {
        soProbe(442, "%s(%u)\n", __FUNCTION__, bn);

        /* change the following line by your code */
        
        SOSuperBlock *sb = soGetSuperBlockPointer();
        
        // Minhas Notas:
        // Inserir o free data block na tail cache
        // Se esta estiver cheia fazer deplete antes de inserir o DB
        // O deplete consiste em colocar todos os DB da tail cache
        //na tail reference data block até esta estar cheia ou até 
        //terem sido colocados todos os DB que estavam na cache
        
        if (sb -> tail_cache.idx == TAIL_CACHE_SIZE) {
			soDepleteTailCache();
		}
        
        sb->tail_cache.ref[sb->tail_idx] = bn;
        sb->tail_cache.idx += 1;
        sb->dz_free += 1;
        
        //binFreeDataBlock(bn);
    }
};

