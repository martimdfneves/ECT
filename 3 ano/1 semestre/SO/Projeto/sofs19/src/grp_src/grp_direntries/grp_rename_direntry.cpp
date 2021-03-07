#include "direntries.h"

#include "core.h"
#include "dal.h"
#include "fileblocks.h"
#include "bin_direntries.h"

#include <string.h>
#include <errno.h>
#include <sys/stat.h>

namespace sofs19
{
    void grpRenameDirEntry(int pih, const char *name, const char *newName)
    {
        soProbe(204, "%s(%d, %s, %s)\n", __FUNCTION__, pih, name, newName);

        /* change the following line by your code */
	
        SOInode* inode = soGetInodePointer(pih);
            SODirEntry dir[DPB];
            for(uint32_t i=0; i<inode->blkcnt; i++)
            {
              sofs19::soReadFileBlock(pih, i, dir);
              for(uint32_t j=0; j<DPB; j++)
              {
                if(strcmp(dir[j].name, name)==0) //encontrar o nome;
                {
                  strcpy(dir[j].name, newName);
                  sofs19::soWriteFileBlock(pih, i, dir);
                  break;
                }
              }
            }
	
        //binRenameDirEntry(pih, name, newName);
    }
};
