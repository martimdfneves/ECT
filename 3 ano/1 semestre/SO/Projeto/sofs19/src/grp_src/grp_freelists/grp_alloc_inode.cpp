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
#include <sys/stat.h>
#include <string.h>

#include <iostream>

#include "core.h"
#include "dal.h"
#include "freelists.h"
#include "bin_freelists.h"

namespace sofs19
{
    uint32_t grpAllocInode(uint32_t type, uint32_t perm)
    {
        soProbe(401, "%s(0x%x, 0%03o)\n", __FUNCTION__, type, perm);
        
        SOSuperBlock *superBlock = soGetSuperBlockPointer();
        if(superBlock->ifree == 0){
            throw SOException(ENOSPC,__FUNCTION__);
        }
        if(type != S_IFREG && type != S_IFDIR && type != S_IFLNK ){
            throw SOException(EINVAL,__FUNCTION__);
        }
        if( perm < 0000 or perm > 0777){
            throw SOException(EINVAL,__FUNCTION__);
        }
        
        //libertar memoria
        superBlock->ifree--;

        uint32_t val = superBlock-> ihead; 
        uint32_t handler = soOpenInode(val);
        //inicializar inode
        SOInode* inode = soGetInodePointer(handler);

        superBlock->ihead = inode->next;

        inode->mode = type;
        inode->lnkcnt = 0;
        inode->owner = getuid(); 
        inode->group = getgid();
        inode->size = 0;
        inode->blkcnt = 0;
        inode->ctime = time(NULL);
        inode->mtime = time(NULL);
        inode->atime = time(NULL);
        int i;
        for(i = 0; i < N_DIRECT; i++){
            inode->d[i] = NullReference; 
        }
        for(i = 0; i < N_INDIRECT; i++){
            inode->i1[i] = NullReference; 
        }
        for(i = 0;i < N_DOUBLE_INDIRECT;i++){
            inode->i2[i] = NullReference;  
        }
    
        soSaveInode(handler);
        soSaveSuperBlock();
        soCloseInode(handler);
        return val;
        
        //return binAllocInode(type,perm);
    }
};

