#include "grp_fileblocks.h"

#include "freelists.h"
#include "dal.h"
#include "core.h"
#include "bin_fileblocks.h"

#include <errno.h>

#include <iostream>
#include <cstring>

namespace sofs19
{

#if true
    static uint32_t grpAllocIndirectFileBlock(SOInode * ip, uint32_t afbn);
    static uint32_t grpAllocDoubleIndirectFileBlock(SOInode * ip, uint32_t afbn);
#endif

    /* ********************************************************* */

    uint32_t grpAllocFileBlock(int ih, uint32_t fbn)
    {
        soProbe(302, "%s(%d, %u)\n", __FUNCTION__, ih, fbn);

        /* change the following two lines by your code */
        /* return binAllocFileBlock(ih, fbn); */
	/*
        if((fbn<0) || (fbn>(N_DIRECT + N_INDIRECT*RPB + N_DOUBLE_INDIRECT*RPB))) throw SOException(EINVAL, __FUNCTION__);

        SOInode* inode_p = soGetInodePointer(ih);
        uint32_t block_number;

        if (fbn<N_DIRECT) {

            block_number=soAllocDataBlock();

            if(inode_p->d[fbn-1]==NullReference)
                inode_p->blkcnt++;

            inode_p->d[fbn-1]=block_number;
        } else if (fbn<(N_DIRECT+N_INDIRECT*RPB)) block_number=grpAllocIndirectFileBlock(inode_p,fbn);

        else block_number=grpAllocDoubleIndirectFileBlock(inode_p,fbn);

        return block_number;*/
        return binAllocFileBlock(ih,fbn);
    }

    /* ********************************************************* */

#if true
    /*
    */
    static uint32_t grpAllocIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(302, "%s(%d, ...)\n", __FUNCTION__, afbn);

        /* change the following two lines by your code */
        /* throw SOException(ENOSYS, __FUNCTION__); */
        /* return 0; */

        uint32_t block_number;
        uint32_t buffer[RPB];

        int i1_n = (afbn-N_DIRECT)/RPB;
        int i1_n_idx = (afbn-N_DIRECT)%RPB;

        if (ip->i1[i1_n] == NullReference) {

            ip->i1[i1_n] = soAllocDataBlock();
            memset(buffer, NullReference, sizeof(buffer));

        } else soReadDataBlock(ip->i1[i1_n], buffer);

        if(buffer[i1_n_idx] == NullReference) {

            block_number = soAllocDataBlock();
            buffer[i1_n_idx] = block_number;
            ip->blkcnt++;

        }

        soWriteDataBlock(ip->i1[i1_n],  &buffer);
        return block_number;
    }
#endif

    /* ********************************************************* */

#if true
    /*
    */
    static uint32_t grpAllocDoubleIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(302, "%s(%d, ...)\n", __FUNCTION__, afbn);

        /* change the following two lines by your code */
        /* throw SOException(ENOSYS, __FUNCTION__); */
        /* return 0; */

        uint32_t indir_buffer[RPB],
                 indir2_buffer[RPB];

        uint32_t indir_block_number,
                 indir2_block_number,
                 block_number;

        int i1_n =     (afbn-(N_INDIRECT*RPB)-N_DIRECT)/RPB;
        int i1_n_idx = (afbn-(N_INDIRECT*RPB)-N_DIRECT)%RPB;

        if(ip->i2[i1_n] == NullReference) {

            indir_block_number = soAllocDataBlock();
            ip->i2[i1_n]       = indir_block_number;
            memset(indir_buffer, NullReference, sizeof(indir_buffer));
            indir_buffer[0]    = indir_block_number;
            soWriteDataBlock(indir_block_number, indir_buffer);

            indir2_block_number = soAllocDataBlock();
            memset(indir2_buffer, NullReference, sizeof(indir2_buffer));
            indir2_buffer[0]    = indir2_block_number;
            soWriteDataBlock(ip->i2[i1_n], indir2_buffer);

        } else soReadDataBlock(ip->i2[ i1_n], indir2_buffer);

        if(indir_buffer[i1_n_idx] == NullReference) {

            block_number = soAllocDataBlock();
            indir_buffer[i1_n_idx] = block_number;
            ip->blkcnt++;

        } else if (indir_buffer[i1_n_idx] != NullReference && indir2_buffer[ i1_n_idx] == NullReference) {

            block_number = soAllocDataBlock();
            indir2_buffer[i1_n_idx] = block_number;
            ip->blkcnt++;

        }
        soWriteDataBlock(ip->i2[i1_n], &indir2_buffer);
        return block_number;
    }
#endif
};
