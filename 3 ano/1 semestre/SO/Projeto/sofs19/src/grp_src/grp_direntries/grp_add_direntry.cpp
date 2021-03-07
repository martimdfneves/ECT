#include "direntries.h"

#include "core.h"
#include "dal.h"
#include "fileblocks.h"
#include "bin_direntries.h"

#include <errno.h>
#include <string.h>
#include <sys/stat.h>

namespace sofs19
{
    void grpAddDirEntry(int pih, const char *name, uint32_t cin)
    {
        soProbe(202, "%s(%d, %s, %u)\n", __FUNCTION__, pih, name, cin);

        /* change the following line by your code */

	/*
        SOInode *inode_parent = soGetInodePointer(pih);
        SODirEntry direntry[DPB];
        uint32_t pos;
        uint32_t last_idx, last_Block;
        uint32_t block_number, direntry_inode = NullReference;

        direntry_inode = soGetDirEntry(pih, name);
        if(direntry_inode != NullReference)
            throw SOException(EEXIST, __FUNCTION__);

        last_Block = (inode_parent->size / sizeof(SODirEntry)) / RPB;
        last_idx   = (inode_parent->size /sizeof(SODirEntry)) % RPB;

        block_number = soGetFileBlock(pih, last_Block);
        if(block_number != NullReference) {

            block_number = soAllocFileBlock(pih, last_Block);

            for (pos = 0; pos < DPB; pos++) {
                direntry[pos].in = NullReference;
                memset(&direntry[pos].name, '\0', SOFS19_MAX_NAME+1);
            }

            strcpy(direntry[0].name, name);
            direntry[0].in = cin;
            soWriteDataBlock(block_number, &direntry);
            inode_parent->size = inode_parent->size + sizeof(SODirEntry);
            return;

            } else {

            soReadFileBlock(pih, last_Block, &direntry);
            strcpy (direntry[last_idx].name, name);
            direntry[last_idx].in = cin;
            soWriteDataBlock(block_number, &direntry);
            inode_parent->size = inode_parent->size + sizeof(SODirEntry);
            return;

            }*/
         binAddDirEntry(pih, name, cin); 
    }
};
