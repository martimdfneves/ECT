#include "direntries.h"

#include "core.h"
#include "dal.h"
#include "fileblocks.h"
#include "direntries.h"
#include "bin_direntries.h"

#include <errno.h>
#include <string.h>
#include <libgen.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>

namespace sofs19
{
    uint32_t grpTraversePath(char *path)
    {
        soProbe(221, "%s(%s)\n", __FUNCTION__, path);
        
        uint32_t iNumber;
	
        char *pathCopy = strdupa(path);												
        char *base = strdupa(basename(pathCopy));									
        char *dir = dirname(pathCopy);												
        

        if(strlen(path) == 0 || strlen(path)> SOFS19_MAX_NAME || path[0]!='/'){
    	    throw SOException(EINVAL, __FUNCTION__);
        }

        uint32_t length = strlen(path);
        if(length == 1 && path[0] == '/')											
        {
            iNumber = 0;															
            return iNumber;														
        }


        if(strcmp(dir,"/") == 0)													
        {
            int ih = soOpenInode(0);														
            if(!soCheckInodeAccess(ih, 01)) throw SOException(EACCES,__FUNCTION__);		
            iNumber = soGetDirEntry(ih, base);										
            if(iNumber == NullReference) throw SOException(ENOENT,__FUNCTION__); 	
            soSaveInode(ih);						
            return iNumber;
        }

        int ih = soOpenInode(soTraversePath(dir));										
        if(!soCheckInodeAccess(ih, 01)) throw SOException(EACCES,__FUNCTION__);			
        iNumber = soGetDirEntry(ih, base);											
        if(iNumber == NullReference) throw SOException(ENOENT,__FUNCTION__);
        soSaveInode(ih);															
        return iNumber;
        /* change the following line by your code */
        //return binTraversePath(path);
    }
};

