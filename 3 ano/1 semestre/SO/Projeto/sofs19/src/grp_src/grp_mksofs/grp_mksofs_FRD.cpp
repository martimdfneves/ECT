#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "bin_mksofs.h"

#include <string.h>
#include <inttypes.h>

namespace sofs19
{
    /*
       filling in the contents of the root directory:
       the first 2 entries are filled in with "." and ".." references
       the other entries are empty.
       */
    void grpFillRootDir(uint32_t itotal)
    {
        soProbe(606, "%s(%u)\n", __FUNCTION__, itotal);

        /* change the following line by your code */
        
		SODirEntry dirEntry[DPB];
		memset(dirEntry, 0, SOFS19_MAX_NAME+1);

		uint32_t block;
		block=(itotal/IPB)+1;

		strncpy((char*)dirEntry[0].name, (char*) ".", SOFS19_MAX_NAME+1);
		strncpy((char*)dirEntry[1].name, (char*) "..", SOFS19_MAX_NAME+1);

		for(uint32_t index = 0; index < DPB; index++) {
			if(index > 1){
				strncpy((char*)dirEntry[index].name, (char*) "\0", SOFS19_MAX_NAME+1);
				dirEntry[index].in = NullReference;
			}
			else{
				dirEntry[index].in = 0;
			}
		}

		soWriteRawBlock(block,dirEntry);
		//binFillRootDir(itotal);
	}
};
