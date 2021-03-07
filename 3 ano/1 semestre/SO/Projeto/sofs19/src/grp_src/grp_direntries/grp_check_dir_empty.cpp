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
    bool grpCheckDirEmpty(int ih)
    {
        soProbe(205, "%s(%d)\n", __FUNCTION__, ih);

        /* change the following line by your code */
	
        soProbe(205, "%s(%d)\n", __FUNCTION__, ih);
        SOInode *inode = soGetInodePointer(ih);
        SODirEntry dir[DPB];
        
        for(uint32_t j = 0; j < (inode->size/BlockSize);j++){
            sofs19::soReadFileBlock(ih,j,dir);
            for(uint32_t k = 0;k < DPB;k++){
                if(dir[k].in != NullReference){
                    if(!(strcmp(dir[k].name,"..")== 0 || strcmp(dir[k].name,".") == 0)){
                        return false;
                    }
                }

            }
        }
        return true;
        //return binCheckDirEmpty(ih); 
    }
};
