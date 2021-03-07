#include "grp_fileblocks.h"

#include "dal.h"
#include "core.h"
#include "fileblocks.h"
#include "bin_fileblocks.h"

#include <string.h>
#include <inttypes.h>

namespace sofs19
{
    void grpWriteFileBlock(int ih, uint32_t fbn, void *buf)
    {
        soProbe(332, "%s(%d, %u, %p)\n", __FUNCTION__, ih, fbn, buf);

        /* change the following line by your code */
	/*
        uint32_t block = sofs19::soGetFileBlock(ih, fbn);
        if(block == NullReference)
        {
            block = sofs19::soAllocFileBlock(ih, fbn);
        }
	
        soWriteDataBlock(block, &buf);*/
        binWriteFileBlock(ih, fbn, buf);
    }
};

