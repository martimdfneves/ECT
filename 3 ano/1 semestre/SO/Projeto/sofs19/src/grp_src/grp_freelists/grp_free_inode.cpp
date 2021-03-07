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
    void grpFreeInode(uint32_t in)
    {
        soProbe(402, "%s(%u)\n", __FUNCTION__, in);
	
	SOSuperBlock* sb = soGetSuperBlockPointer();

	if(sb->ifree == 0){
		uint32_t hd_in = soOpenInode(in);
		SOInode* p_in = soGetInodePointer(hd_in);
		p_in->next = NullReference;
		p_in->mode = INODE_FREE;
		p_in->lnkcnt = 0;
		p_in->owner = 0;
		p_in->group = 0;
		p_in->size = 0;
		p_in->blkcnt = 0;
		soSaveInode(hd_in);
		sb->ifree++;
		sb->itail = in;
		sb->ihead = in;
		soSaveSuperBlock();
	}
	else{
		uint32_t hd_last = soOpenInode(sb->itail);
		uint32_t hd_in = soOpenInode(in);
		SOInode* p_last = soGetInodePointer(hd_last);
		SOInode* p_in = soGetInodePointer(hd_in);
		p_in->next = NullReference;
		p_in->mode = INODE_FREE;
		p_in->lnkcnt = 0;
		p_in->owner = 0;
		p_in->group = 0;
		p_in->size = 0;
		p_in->blkcnt = 0;
		soSaveInode(hd_in);
		p_last->next = in;
		soSaveInode(hd_last);

		sb->ifree++;
		sb->itail = in;
		soSaveSuperBlock();

		soCloseInode(hd_last);
		soCloseInode(hd_in);
	}
        //change the following line by your code 
	
        //binFreeInode(in);
    }
};

