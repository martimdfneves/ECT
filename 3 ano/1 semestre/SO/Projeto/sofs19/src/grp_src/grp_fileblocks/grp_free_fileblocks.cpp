#include "grp_fileblocks.h"

#include "freelists.h"
#include "dal.h"
#include "core.h"
#include "bin_fileblocks.h"

#include <inttypes.h>
#include <errno.h>
#include <assert.h>

namespace sofs19
{
#if true
    /* free all blocks between positions ffbn and RPB - 1
     * existing in the block of references given by i1.
     * Return true if, after the operation, all references become NullReference.
     * It assumes i1 is valid.
     */
    static int grpFreeIndirectFileBlocks(SOInode * ip, uint32_t i1, uint32_t ffbn);

    /* free all blocks between positions ffbn and RPB**2 - 1
     * existing in the block of indirect references given by i2.
     * Return true if, after the operation, all references become NullReference.
     * It assumes i2 is valid.
     */
    static int grpFreeDoubleIndirectFileBlocks(SOInode * ip, uint32_t i2, uint32_t ffbn);
#endif

    /* ********************************************************* */

    void grpFreeFileBlocks(int ih, uint32_t ffbn)
    {
        soProbe(303, "%s(%d, %u)\n", __FUNCTION__, ih, ffbn);
	
	
   	uint32_t block_count = 0; 
	SOInode* p_in = soGetInodePointer(ih);
	uint32_t d_remove = 0;
	uint32_t i1_remove = 0;
	uint32_t i2_remove = 0;
	if (ffbn < N_DIRECT){
		d_remove = 1;
		i1_remove = 1;
		i2_remove = 1;
	}
	else if(ffbn < N_DIRECT + N_INDIRECT*256){
		i1_remove = 1;
		i2_remove = 1;
	}
	else{
		i2_remove = 1;
	}

	

	if(d_remove){
		uint32_t *d = p_in->d;
		for(int i = ffbn; i<N_DIRECT; i++){
			if(d[i] != NullReference){
				soFreeDataBlock(d[i]);
				block_count++;
				d[i] = NullReference;
			}
		}
		ffbn = N_DIRECT;
	}
	if(i1_remove){
		ffbn -= N_DIRECT;
		uint32_t clean = 0;
		for(int i = 0; i<N_INDIRECT; i++){
			if(ffbn >= 0 and ffbn < RPB){
				if(clean){
					if(p_in->i1[i] != NullReference){
				       		uint32_t f = grpFreeIndirectFileBlocks(p_in, p_in->i1[i], 0);
						soFreeDataBlock(p_in->i1[i]);
						block_count += f+1;
						p_in->i1[i] = NullReference;
					}
				}
				else{
					if(p_in->i1[i] != NullReference){
						uint32_t f = grpFreeIndirectFileBlocks(p_in, p_in->i1[i], ffbn);
						if(ffbn == 0){
							soFreeDataBlock(p_in->i1[i]);       
							p_in->i1[i] = NullReference;
							block_count++;
						}
						block_count += f;
					}
					clean = 1;
				}
			}
			else{
				ffbn -= RPB;
			}
		}
		ffbn = N_DIRECT + N_INDIRECT*256;	
	}
	if(i2_remove){
		ffbn -= N_DIRECT + N_INDIRECT*256;
		uint32_t clean = 0;
		for(int i = 0; i<N_DOUBLE_INDIRECT; i++){
			if(ffbn >= 0 and ffbn < RPB*RPB){
				if(clean){
					if(p_in->i2[i] != NullReference){
						uint32_t f = grpFreeDoubleIndirectFileBlocks(p_in, p_in->i2[i], 0);
						soFreeDataBlock(p_in->i2[i]);
						block_count += f+1;
						p_in->i2[i] = NullReference;
					}
				}
				else{
					if(p_in->i2[i] != NullReference){
						uint32_t f = grpFreeDoubleIndirectFileBlocks(p_in, p_in->i2[i], ffbn);
						if(ffbn == 0){
							soFreeDataBlock(p_in->i2[i]);
					       		p_in->i2[i] = NullReference;
							block_count++;
						}
						block_count += f;
					}
					clean = 1;
				}
			}
			else{
				ffbn -= RPB*RPB;
			}
		}
	}
	p_in->blkcnt -= block_count;
	soSaveInode(ih);
	//soCloseInode(ih);

	//Guardar Alterações
	
        /* change the following line by your code */
        //binFreeFileBlocks(ih, ffbn);
    }

    /* ********************************************************* */

#if true
    //Depois melhorar a implementação com o campo size para nao fazer fores desnecessarios
    //Corrigir
    static int grpFreeIndirectFileBlocks(SOInode * ip, uint32_t i1, uint32_t ffbn)
    {
        soProbe(303, "%s(..., %u, %u)\n", __FUNCTION__, i1, ffbn);
	int f = 0;
	uint32_t buffer[RPB];
	soReadDataBlock(i1, buffer);
	for(int i = ffbn; i<RPB; i++){
		if(buffer[i] != NullReference){
			soFreeDataBlock(buffer[i]);
			f++;
			buffer[i] = NullReference;
		}
	}
	soWriteDataBlock(i1, buffer);
	return f;
        /* change the following line by your code */
        //throw SOException(ENOSYS, __FUNCTION__); 
    }
#endif

    /* ********************************************************* */

#if true
    static int grpFreeDoubleIndirectFileBlocks(SOInode * ip, uint32_t i2, uint32_t ffbn)
    {
        soProbe(303, "%s(..., %u, %u)\n", __FUNCTION__, i2, ffbn);
	uint32_t buffer[RPB];
	soReadDataBlock(i2, buffer);
	uint32_t clean = 0;
	int f = 0;

	for(int i = 0; i<RPB; i++){
		if(ffbn >= 0 and ffbn < RPB){
			if(clean){
				if(buffer[i] != NullReference){
				 	f += grpFreeIndirectFileBlocks(ip, buffer[i], 0)+1;
				 	soFreeDataBlock(buffer[i]);
				 	buffer[i] = NullReference;
				}
			}
			else{
				if(buffer[i] != NullReference){
					f += grpFreeIndirectFileBlocks(ip, buffer[i], ffbn);
					if(ffbn == 0){
						soFreeDataBlock(buffer[i]);
				 		buffer[i] = NullReference;
						f++;
					}
				}
				clean = 1;
			}
		}
		else{
			ffbn -= RPB;
		}
	
	}
	soWriteDataBlock(i2, buffer);
	return f;
        /* change the following line by your code */
        //throw SOException(ENOSYS, __FUNCTION__); 
    }
#endif

    /* ********************************************************* */
};

