#include "grp_fileblocks.h"

#include "dal.h"
#include "core.h"
#include "bin_fileblocks.h"

#include <errno.h>

namespace sofs19
{
    /* ********************************************************* */

#if true
    static uint32_t grpGetIndirectFileBlock(SOInode * ip, uint32_t fbn);
    static uint32_t grpGetDoubleIndirectFileBlock(SOInode * ip, uint32_t fbn);
#endif

    /* ********************************************************* */

    uint32_t grpGetFileBlock(int ih, uint32_t fbn)
    {
        soProbe(301, "%s(%d, %u)\n", __FUNCTION__, ih, fbn);

        uint32_t di = (N_INDIRECT*RPB)+N_DIRECT; 
            
        if(fbn < 0 || fbn > ((RPB*RPB*N_DOUBLE_INDIRECT)+di-1)){
            throw SOException(EINVAL,__FUNCTION__);          
        }
        
        SOInode* ip = soGetInodePointer(ih);
        if(fbn < N_DIRECT){
            return ip->d[fbn];
        }else if(fbn < di){
            return grpGetIndirectFileBlock(ip,fbn-N_DIRECT);
        }else{
            return grpGetDoubleIndirectFileBlock(ip,fbn-di);
        }
	
        //return binGetFileBlock(ih,fbn);
    }

    /* ********************************************************* */
#if true
    static uint32_t grpGetIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(301, "%s(%d, ...)\n", __FUNCTION__, afbn);

        uint32_t d[RPB];
        uint32_t p1 = afbn / RPB;
        uint32_t p2 = afbn % RPB;

        if (ip->i1[p1] == NullReference){
            return NullReference;
        }
        else{
            soReadDataBlock(ip->i1[p1], &d);
            return d[p2];
        }
    }
#endif

    /* ********************************************************* */
#if true
    static uint32_t grpGetDoubleIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(301, "%s(%d, ...)\n", __FUNCTION__, afbn);

        uint32_t d[RPB];
        uint32_t p1 = afbn / (RPB * RPB);
        uint32_t p2 = afbn / RPB - (p1 * RPB);
        uint32_t p3 = afbn % RPB;

        if ( ip -> i2[p1] == NullReference){
            return NullReference;
        }
        else{
            soReadDataBlock(ip -> i2[p1], &d);

            if(d[p2] == NullReference){
                return NullReference;
            }
            else{
                soReadDataBlock(d[p2],&d);
                return d[p3];
            }
            
        }
        
    }
#endif
};

