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
    static int grpStrEquals(const char* s1, const char* s2);


    uint32_t grpDeleteDirEntry(int pih, const char *name)
    {
        soProbe(203, "%s(%d, %s)\n", __FUNCTION__, pih, name);


	/* change the following line by your code */
	
	SOInode* in_p = soGetInodePointer(pih);
	SODirEntry e[DPB];
	uint32_t inode_r;


	for(int i = 0; i < (in_p->size)/BlockSize; i++){

		soReadFileBlock(pih,i,e);
		for(int w = 0; w < DPB; w++){
			if(grpStrEquals(e[w].name, name)){
				for(int j = 0; j<=SOFS19_MAX_NAME; j++) e[w].name[j] = 0;
				inode_r = e[w].in;
				e[w].in = NullReference;
				soWriteFileBlock(pih,i,e);
				return inode_r;
			}
		}
	}
	throw SOException(ENOENT,__FUNCTION__);
	
        /* change the following line by your code */
        //return binDeleteDirEntry(pih, name);
    }
    
    static int grpStrEquals(const char* s1, const char* s2)
    {
	    char* s1_t = (char*)s1;
	    char* s2_t = (char*)s2;
	    while(*s1_t == *s2_t){
		    if(*s1_t == 0) return 1;
		    s1_t++;
		    s2_t++;
	    }
	    return 0;
    }

};

