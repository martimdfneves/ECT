#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "bin_mksofs.h"

#include <string.h>
#include <time.h>
#include <unistd.h>
#include <sys/stat.h>
#include <inttypes.h>

namespace sofs19
{
    void grpFillInodeTable(uint32_t itotal, bool set_date)
    {
        soProbe(604, "%s(%u)\n", __FUNCTION__, itotal);
        
        SOInode table[IPB];
        uint32_t nblocks = itotal/IPB;
        uint32_t count = 1;
        for(uint32_t i = 0; i<nblocks; i++){

            for(uint32_t aux = 0; aux<IPB; aux++){
                if(i == 0 && aux == 0 ){
                    table[0].mode = S_IRUSR | S_IWUSR | S_IXUSR | S_IRGRP | S_IWGRP | S_IXGRP | S_IROTH | S_IXOTH | S_IFDIR;
                    table[0].lnkcnt = 2;
                    table[0].owner = getuid();
                    table[0].group = getgid();
                    table[0].size = sizeof(SODirEntry)*2;
                    table[0].blkcnt = 1;
                    table[0].next = 1;
                    table[0].atime =  NullReference;
                    table[0].mtime =  NullReference;
                    if(set_date){
                        table[0].ctime =  time(0);
                    }
                    for(uint32_t j = 0; j < N_DIRECT; j++)
                    {
                        if(j == 0 ){
                            table[0].d[j] = 0;
                        }else{
                            table[0].d[j] = NullReference;
                        }
                    }
                    for(uint32_t j = 0; j < N_INDIRECT; j++)
                    {
                    table[0].i1[j] = NullReference;
                    }
                    for(uint32_t j = 0; j < N_DOUBLE_INDIRECT; j++)
                    {
                    table[0].i2[j] = NullReference;
                    }
                }
                else {
                    table[aux].mode = INODE_FREE;
                    table[aux].lnkcnt = 0;
                    table[aux].owner = 0;
                    table[aux].group = 0;
                    table[aux].size = 0;
                    table[aux].blkcnt = 0; 
                    table[aux].next = ++count;
                    if (i == nblocks-1 && aux == IPB - 1){
                        table[aux].next = NullReference;
                    }
                    table[0].atime =  NullReference;
                    table[0].mtime =  NullReference;
                    if(set_date){
                        table[0].ctime =  time(0);
                    }

                    for(uint32_t j = 0; j < N_DIRECT; j++){
                        table[aux].d[j] = NullReference;
                    }
                    for(uint32_t j = 0; j < N_INDIRECT; j++){
                        table[aux].i1[j] = NullReference;
                    }
                    for(uint32_t j = 0; j < N_DOUBLE_INDIRECT; j++){
                        table[aux].i2[j] = NullReference;
                    }
                    //count++;
                }
            }
            
            soWriteRawBlock(i+1, table); 
            
        }
        //return binFillInodeTable(itotal, set_date);
    }
};

