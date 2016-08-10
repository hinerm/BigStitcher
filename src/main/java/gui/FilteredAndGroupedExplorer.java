package gui;

import ij.ImageJ;
import input.FractalSpimDataGenerator;
import input.GenerateSpimData;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import spim.fiji.datasetmanager.LightSheetZ1;
import spim.fiji.spimdata.explorer.ViewSetupExplorer;
import mpicbg.spim.data.SpimData;
import mpicbg.spim.data.XmlIoSpimData;
import mpicbg.spim.data.generic.AbstractSpimData;
import mpicbg.spim.data.generic.XmlIoAbstractSpimData;
import net.imglib2.FinalInterval;
import net.imglib2.Interval;
import net.imglib2.RealLocalizable;
import net.imglib2.realtransform.AffineTransform2D;
import net.imglib2.realtransform.AffineTransform3D;

public class FilteredAndGroupedExplorer< AS extends AbstractSpimData< ? >, X extends XmlIoAbstractSpimData< ?, AS > >
{
	final JFrame frame;
	FilteredAndGroupedExplorerPanel<AS, X> panel;
	
	public FilteredAndGroupedExplorer( final AS data, final String xml, final X io )
	{
		frame = new JFrame( "Stitching Explorer" );
		panel = new FilteredAndGroupedExplorerPanel< AS, X >( this, data, xml, io );

		frame.add( panel, BorderLayout.CENTER );
		frame.setSize( panel.getPreferredSize() );

		frame.addWindowListener(
				new WindowAdapter()
				{
					@Override
					public void windowClosing( WindowEvent evt )
					{
						quit();
					}
				});

		frame.pack();
		frame.setVisible( true );

		// set the initial focus to the table
		panel.table.requestFocus();
	}
	
	public void quit()
	{
		for ( final SelectedViewDescriptionListener< AS > l : panel.getListeners() )
			l.quit();

		panel.getListeners().clear();
		
		frame.setVisible( false );
		frame.dispose();

		StitchingExplorerPanel.currentInstance = null;
	}
	
	public AS getSpimData() { return panel.getSpimData(); }
	public FilteredAndGroupedExplorerPanel< AS, X > getPanel() { return panel; }
	public JFrame getFrame() { return frame; }
	public void addListener( final SelectedViewDescriptionListener< AS > listener ) { panel.addListener( listener ); }
	public ArrayList< SelectedViewDescriptionListener< AS > > getListeners() { return panel.getListeners(); }

	public static void main( String[] args )
	{
		//new ImageJ();
		//new ViewSetupExplorer<>( GenerateSpimData.grid3x2(), null, null );

		new FilteredAndGroupedExplorer< SpimData, XmlIoSpimData >( GenerateSpimData.grid3x2(), null, null );
		//new FilteredAndGroupedExplorer<SpimData, XmlIoSpimData>( new LightSheetZ1().createDataset(), null, null );
		//new FilteredAndGroupedExplorer< SpimData, XmlIoSpimData >( FractalSpimDataGenerator.createVirtualSpimData(), null, null );
	
	}
	
}
